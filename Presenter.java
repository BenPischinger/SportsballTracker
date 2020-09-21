package sportsballtracker;

public class Presenter
{
    private Model model;

    private View view;

    private Dialog dialog;

    private UndoRedoManager undoRedoManager;

    public Presenter()
    {
        this.undoRedoManager = new UndoRedoManager();
    }

    public void setModel(Model model)
    {
        this.model = model;
    }

    public void setView(View view)
    {
        this.view = view;
    }

    public void setDialog(Dialog dialog)
    {
        this.dialog = dialog;
    }

    public void undo()
    {
        undoRedoManager.undo();
    }

    public void saveFile()
    {
        model.writeToFile();
    }

    public void addMatch(Match match)
    {
        model.addMatch(match);
        view.addMatch(match);
        view.updateScores(model.getAllScores(), model.getAllMatches());
    }

    public void deleteMatch(Match match)
    {
        model.deleteMatch(match);
        view.updateScores(model.getAllScores(), model.getAllMatches());
    }

    public void showDialog()
    {
        dialog.showDialog();
    }

}
