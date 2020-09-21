package sportsballtracker;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dialog
{
    private Presenter presenter;

    private Match match;

    private Stage stage;

    private BorderPane borderPane;

    private TextField team1, team2, score1, score2;

    private Button add, cancel;

    private Label label;

    public void initDialog()
    {
        stage = new Stage();

        borderPane = new BorderPane();

        VBox contentBox = new VBox();
        contentBox.setSpacing(5.0);
        contentBox.setPadding(new Insets(5.0));

        HBox row1 = new HBox();
        row1.setSpacing(5.0);
        HBox row2 = new HBox();
        row1.setSpacing(5.0);
        HBox row3 = new HBox();
        row3.setSpacing(5.0);

        team1 = new TextField();
        team1.setPromptText("Team 1");

        team2 = new TextField();
        team2.setPromptText("Team 2");

        row1.getChildren().addAll(new Label("Teams: "), team1, new Label(" - "), team2);

        score1 = new TextField();
        score1.setPromptText("Score 1");

        score2 = new TextField();
        score2.setPromptText("Score 2");

        row2.getChildren().addAll(new Label("Scores: "), score1, new Label(" : "), score2);

        add = new Button("Add");
        add.setOnAction(e ->
        {
            addMatch();
        });

        cancel = new Button("Cancel");
        cancel.setOnAction(e -> cancel());

        row3.getChildren().addAll(add, cancel);

        contentBox.getChildren().addAll(row1, row2, row3);

        borderPane.setCenter(contentBox);

        label = new Label("");

        borderPane.setBottom(label);

        stage.setScene(new Scene(borderPane));
        stage.setTitle("Add Match");

    }

    public void setPresenter(Presenter presenter)
    {
        this.presenter = presenter;
    }

    public void showDialog()
    {
        stage.show();
    }

    public void addMatch()
    {
        if (score1.getText().isEmpty() || score2.getText().isEmpty() || Integer.parseInt(score1.getText()) < 0 || Integer.parseInt(score2.getText()) < 0)
        {
            label.setText("Scores: Invalid Entry");
        }
        else if (team1.getText().isEmpty() || team2.getText().isEmpty() || team1.getText().equals(team2.getText()))
        {
            label.setText("Teams: Invalid Entry");
        }
        else
        {
            match = new Match(team1.getText(), team2.getText(), Integer.parseInt(score1.getText()), Integer.parseInt(score2.getText()));
            presenter.addMatch(match);
            cancel();
        }
    }

    public void cancel()
    {
        team1.clear();
        team2.clear();
        score1.clear();
        score2.clear();
        label.setText("");
        stage.hide();
    }

}
