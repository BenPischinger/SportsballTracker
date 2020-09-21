package sportsballtracker;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class View
{
    private Presenter presenter;

    private ListView<Match> matchListView;

    private ListView<ScoreEntry> scoreEntryListView;

    private BorderPane borderPane;

    private Button add, delete;

    private MenuBar menuBar;

    private Menu file;

    private MenuItem save;

    private Label status;

    public void initView()
    {
        borderPane = new BorderPane();

        matchListView = new ListView<Match>();

        scoreEntryListView = new ListView<ScoreEntry>();

        HBox listViewBox = new HBox();
        listViewBox.setSpacing(5.0);
        listViewBox.setPadding(new Insets(5.0));

        listViewBox.getChildren().addAll(matchListView, scoreEntryListView);

        borderPane.setCenter(listViewBox);

        add = new Button("Add Match");
        add.setOnAction(e -> presenter.showDialog());

        delete = new Button("Delete Match");
        delete.setOnAction(e ->
        {
            if (!matchListView.getSelectionModel().isEmpty())
            {
                deleteMatch(matchListView.getSelectionModel().getSelectedItem());
            }
        });

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(5.0);
        buttonBox.setPadding(new Insets(5.0));

        status = new Label();

        buttonBox.getChildren().addAll(add, delete, status);

        borderPane.setBottom(buttonBox);

        menuBar = new MenuBar();
        file = new Menu("File");
        save = new MenuItem("Save File");
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        save.setOnAction(e ->
        {
            presenter.saveFile();
            status.setText("Successfully Saved");

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev ->
            {
                status.setText("");
            }));
            
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });
        file.getItems().addAll(save);
        menuBar.getMenus().add(file);

        borderPane.setTop(menuBar);

    }

    public void setPresenter(Presenter presenter)
    {
        this.presenter = presenter;
    }

    public void addMatch(Match match)
    {
        matchListView.getItems().add(match);
    }

    public void deleteMatch(Match match)
    {
        matchListView.getItems().remove(match);
        presenter.deleteMatch(match);
    }

    public void updateScores(ScoreEntry[] scoreEntries, Match[] matchEntries)
    {
        matchListView.getItems().clear();
        matchListView.getItems().addAll(matchEntries);

        scoreEntryListView.getItems().clear();
        scoreEntryListView.getItems().addAll(scoreEntries);
    }

    public void setStatus(String string)
    {
        status.setText(string);
    }

    public BorderPane getView()
    {
        return borderPane;
    }

}
