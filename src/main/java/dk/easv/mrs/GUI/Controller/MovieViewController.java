package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    private MovieModel movieModel;

    @FXML
    private TextField txtTitle, txtYear;


    @FXML
    private TableView<Movie> tblMovies;

    @FXML
    private TableColumn<Movie, String> colTitle;

    @FXML
    private TableColumn<Movie, Integer> colYear;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        tblMovies.setItems(movieModel.getObservableMovies());
        lstMovies.setItems(movieModel.getObservableMovies());

        tblMovies.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                {
                    //lambda udtryk
                    //System.out.println(newValue);
                    if (newValue != null){
                    txtTitle.setText(newValue.getTitle());
                    txtYear.setText(newValue.getYear() + "");

                }
                else
        {
            txtTitle.setText("");
            txtYear.setText("");

        }
        });




        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    private void btnHandleClick(ActionEvent actionEvent) throws Exception {

        // get user movie data from UI
        String title = txtTitle.getText();
        int year = Integer.parseInt(txtYear.getText());

        // new movie object
        Movie newMovie = new Movie(-1, year, title);

        // call model to create the movie in the dal
        movieModel.createMovie(newMovie);
    }
}
