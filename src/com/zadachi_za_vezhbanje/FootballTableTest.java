package zadachi_za_vezhbanje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */

enum Result {
    WIN,
    LOSS,
    DRAW
}
class TeamMetrics{
    private String teamName;
    private Integer gamesPlayed;
    private Integer wins;
    private Integer draws;
    private Integer losses;
    private Integer points;
    private Integer goalsG=0;
    private Integer goalsT=0;
    TeamMetrics(String teamName){
        this.teamName = teamName;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.points = 0;
    }
    public TeamMetrics updateMetric(Result result,int[] goals){
        this.goalsG+=goals[0];
        this.goalsT+=goals[1];
        gamesPlayed++;
        if(result == Result.DRAW){
            this.draws++;
            this.points+=1;
        }else if(result.equals(Result.WIN)){
            this.wins++;
            this.points+=3;
        }else{
            this.losses++;
        }

        return this;
    }

    @Override
    public String toString() {
//        Team                   P    W    D    L  PTS
//        1. Liverpool          9    8    0    1   24
        String l = String.format("%-15s%5d%5d%5d%5d%5d",this.teamName,this.gamesPlayed,this.wins,this.draws,this.losses,this.points);
        return l;

    }

    public String getTeamName() {
        return teamName;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public Integer getWins() {
        return wins;
    }

    public Integer getDraws() {
        return draws;
    }

    public Integer getLosses() {
        return losses;
    }

    public Integer getPoints() {
        return points;
    }
    public Integer goalDiff(){
        return  this.goalsG-this.goalsT;
    }
}
class FootballTable{

    private HashMap<String, TeamMetrics> table;
    FootballTable(){
        table = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, Integer homeScore, Integer awayScore){
        if(homeScore.equals(awayScore)){
            this.table.compute(homeTeam,(k,v)->
                    (v==null)? new TeamMetrics(k).updateMetric(Result.DRAW,new int[]{homeScore,awayScore}):v.updateMetric(Result.DRAW,new int[]{homeScore,awayScore}));
            this.table.compute(awayTeam,(k,v)->
                    (v==null)? new TeamMetrics(k).updateMetric(Result.DRAW,new int[]{awayScore,homeScore}):v.updateMetric(Result.DRAW,new int[]{awayScore,homeScore}));
        }else if(homeScore>awayScore){
            this.table.compute(homeTeam,(k,v)->
                    (v==null)? new TeamMetrics(k).updateMetric(Result.WIN,new int[]{homeScore,awayScore}):v.updateMetric(Result.WIN,new int[]{homeScore,awayScore}));
            this.table.compute(awayTeam,(k,v)->
                    (v==null)? new TeamMetrics(k).updateMetric(Result.LOSS,new int[]{awayScore,homeScore}):v.updateMetric(Result.LOSS,new int[]{awayScore,homeScore}));
        }else{
            this.table.compute(homeTeam,(k,v)->
                    (v==null)? new TeamMetrics(k).updateMetric(Result.LOSS,new int[]{homeScore,awayScore}):v.updateMetric(Result.LOSS,new int[]{homeScore,awayScore}));
            this.table.compute(awayTeam,(k,v)->
                    (v==null)? new TeamMetrics(k).updateMetric(Result.WIN,new int[]{awayScore,homeScore}):v.updateMetric(Result.WIN,new int[]{awayScore,homeScore}));
        }
    }
    public void printTable(){
        ArrayList<TeamMetrics> teams = this.table.values().stream().sorted(Comparator.comparing(TeamMetrics::getPoints,Comparator.reverseOrder())
                .thenComparing(TeamMetrics::goalDiff, Comparator.reverseOrder())
                        .thenComparing(TeamMetrics::getTeamName))
                .collect(Collectors.toCollection(ArrayList::new));
        for (int i=0;i<teams.size();i++){
            String line = teams.get(i).toString();
            System.out.println(String.format("%2d. %s",(i+1),line));
        }
    }

}


public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while((line = reader.readLine()) !=null && !line.equals("END")){
            String[] parts = line.split(";");
            table.addGame(parts[0], parts[1],
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]));
        }
//        reader.lines()
//                .map(line -> line.split(";"))
//                .forEach(parts -> table.addGame(parts[0], parts[1],
//                        Integer.parseInt(parts[2]),
//                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here


