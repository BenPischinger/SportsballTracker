package sportsballtracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Presenter presenter = new Presenter();
        View view = new View();
        Dialog dialog = new Dialog();
        Model model = new Model();

        view.initView();
        dialog.initDialog();
        
        view.setPresenter(presenter);
        dialog.setPresenter(presenter);

        presenter.setView(view);
        presenter.setDialog(dialog);
        presenter.setModel(model);
        
        model.createFile();
        model.loadFromFile();
        view.updateScores(model.getAllScores(), model.getAllMatches());
        
        primaryStage.setScene(new Scene(view.getView(), 500, 500));
        primaryStage.setTitle("Sportsball Tracker");
        primaryStage.show();
    }

}
