package sportsballtracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Model
{
    private List<Match> matches;

    private List<ScoreEntry> scores;

    private File matchFile;

    private File scoreFile;

    public Model()
    {
        matches = new ArrayList<>();
        scores = new ArrayList<>();
    }

    public void addMatch(Match m)
    {
        matches.add(m);
        addScores(m.getTeamHome(), m.getGoalsHome(), m.getGoalsGuest());
        addScores(m.getTeamGuest(), m.getGoalsGuest(), m.getGoalsHome());
        scores.sort(Comparator.comparingInt((p) -> -p.getScore()));
    }

    private void addScores(String team, int goalsTeam, int goalsOpponent)
    {
        ScoreEntry newSe = new ScoreEntry(team, goalsTeam, goalsOpponent);
        int index = scores.indexOf(newSe);
        if (index == -1)
        {
            scores.add(newSe);
        }
        else
        {
            ScoreEntry oldSe = scores.get(index);
            oldSe.add(newSe);
        }
    }

    public void deleteMatch(Match m)
    {
        matches.remove(m);
        subtractScores(m.getTeamHome(), m.getGoalsHome(), m.getGoalsGuest());
        subtractScores(m.getTeamGuest(), m.getGoalsGuest(), m.getGoalsHome());
        scores.sort(Comparator.comparingInt((p) -> -p.getScore()));
    }

    private void subtractScores(String team, int goalsTeam, int goalsOpponent)
    {
        ScoreEntry newSe = new ScoreEntry(team, goalsTeam, goalsOpponent);
        int index = scores.indexOf(newSe);
        if (index >= 0)
        {
            ScoreEntry oldSe = scores.get(index);
            oldSe.subtract(newSe);
        }
    }

    public Match[] getAllMatches()
    {
        return matches.toArray(new Match[0]);
    }

    public ScoreEntry[] getAllScores()
    {
        return scores.toArray(new ScoreEntry[0]);
    }

    public void createFile()
    {
        try
        {
            matchFile = new File("src/sportsballtracker/matchFile.txt");
            scoreFile = new File("src/sportsballtracker/scoreFile.txt");

            if (matchFile.createNewFile() && scoreFile.createNewFile())
            {
                System.out.println("Files created: " + matchFile.getName() + " and " + scoreFile.getName());
            }
            else
            {
                System.out.println("File already exists.");
            }
        }
        catch (IOException e)
        {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public void writeToFile()
    {
        try
        {
            FileOutputStream matchWriter = new FileOutputStream("src/sportsballtracker/matchFile.txt");
            FileOutputStream scoreWriter = new FileOutputStream("src/sportsballtracker/scoreFile.txt");

            ObjectOutputStream objectMatchWriter = new ObjectOutputStream(matchWriter);
            ObjectOutputStream objectScoreWriter = new ObjectOutputStream(scoreWriter);

            objectMatchWriter.writeObject(matches);
            objectScoreWriter.writeObject(scores);

            objectMatchWriter.flush();
            objectMatchWriter.close();

            objectScoreWriter.flush();
            objectScoreWriter.close();

            matchWriter.close();

            scoreWriter.close();

            System.out.println("Successfully wrote to the files");
        }
        catch (IOException e)
        {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public void loadFromFile()
    {
        try
        {
            FileInputStream matchReader = new FileInputStream(matchFile);
            FileInputStream scoreReader = new FileInputStream(scoreFile);

            if (matchReader.toString().isEmpty() || scoreReader.toString().isEmpty())
            {
                return;
            }
            else
            {

                ObjectInputStream objectMatchReader = new ObjectInputStream(matchReader);
                ObjectInputStream objectScoreReader = new ObjectInputStream(scoreReader);

                matches = (List<Match>) objectMatchReader.readObject();
                scores = (List<ScoreEntry>) objectScoreReader.readObject();
            }

        }
        catch (

        IOException e)
        {
            e.printStackTrace();
        }
        catch (

        ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
