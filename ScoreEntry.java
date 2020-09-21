package sportsballtracker;

import java.io.Serializable;

class ScoreEntry implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2L;

    private String team;

    private int score;

    private int goals;

    private int recieved;

    private int wins;

    private int ties;

    private int losses;

    public ScoreEntry(String team, int goalsActive, int goalsPassive)
    {
        if (team == null || team.length() == 0)
        {
            throw new IllegalArgumentException("Ungültige Team-Kennung");
        }
        if (goalsActive < 0 || goalsPassive < 0)
        {
            throw new IllegalArgumentException("Negative Torezahl");
        }
        this.team = team;
        this.goals += goalsActive;
        this.recieved += goalsPassive;
        update(goalsActive, goalsPassive);
    }

    private void update(int gA, int gP)
    {
        if (gA > gP)
        {
            score += 3;
            wins += 1;
        }
        else if (gA == gP)
        {
            score += 1;
            ties += 1;
        }
        else if (gA < gP)
        {
            losses += 1;
        }
    }

    public void add(ScoreEntry se)
    {
        if (!team.equals(se.team))
        {
            return;
        }
        score += se.score;
        goals += se.goals;
        recieved += se.recieved;
        wins += se.wins;
        ties += se.ties;
        losses += se.losses;
        
    }

    public void subtract(ScoreEntry se)
    {
        if (!team.equals(se.team))
        {
            return;
        }
        score -= se.score;
        goals -= se.goals;
        recieved -= se.recieved;
        wins -= se.wins;
        ties -= se.ties;
        losses -= se.losses;
    }

    public String getTeam()
    {
        return team;
    }

    public int getScore()
    {
        return score;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof ScoreEntry))
        {
            return false;
        }
        return team.equals(((ScoreEntry) o).team);
    }

    public String toString()
    {
        return team + ": " + score + " (" + goals + ":" + recieved + " " + wins + "/" + ties + "/" + losses + ")";
    }
}
