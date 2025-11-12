package laboratoriski.lab3;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Ad implements Comparable<Ad>{
    private String id;
    private String category;
    private double bidValue;
    private double ctr;
    private String content;
    public Ad(String id, String category, double bidValue, double ctr, String content) {
        this.id = id;
        this.category = category;
        this.bidValue = bidValue;
        this.ctr = ctr;
        this.content = content;
    }

    @Override
    public String toString() {
        return this.id+" "+this.category+" (bid="+String.format("%.2f",this.bidValue)+", ctr="+String.format("%.2f",(this.ctr*100))+"%) "+this.content;
    }

    @Override
    public int compareTo(Ad o) {
        if(this.bidValue < o.bidValue){
            return -1;
        }else if(this.bidValue >  o.bidValue){
            return 1;
        }else{
            if(this.id.compareTo(this.id)>0){
                return 1;
            }else{
                return -1;
            }
        }
    }

    public double getBidValue() {
        return bidValue;
    }
    public double getCtr() {
        return this.ctr;
    }
    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }
}
class AdRequest{
    private String id;
    private String category;;
    private double floorBid;
    private String keywords;

    public AdRequest(String id, String category, double floorBid, String keywords) {
        this.id = id;
        this.category = category;
        this.floorBid = floorBid;
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return this.id+" ["+this.category+"] (floor="+this.floorBid+"): "+this.keywords;
    }

    public String getId() {
        return this.id;
    }
    public String getCategory() {
        return category;
    }

    public double getFloorBid() {
        return floorBid;
    }

    public String getKeywords() {
        return keywords;
    }
}
class AdNetwork {
    private ArrayList<Ad> ads;
    private int relevanceScore(Ad ad, AdRequest req) {
        int score = 0;
        if (ad.getCategory().equalsIgnoreCase(req.getCategory())) score += 10;
        String[] adWords = ad.getContent().toLowerCase().split("\\s+");
        String[] keywords = req.getKeywords().toLowerCase().split("\\s+");
        for (String kw : keywords) {
            for (String aw : adWords) {
                if (kw.equals(aw)) score++;
            }
        }
        return score;
    }
    void readAds(BufferedReader br) throws IOException {
        String line;
        if( this.ads==null)
            this.ads = new ArrayList<Ad>();
        while ((line = br.readLine()) != null) {
            // System.out.println(line.length());
            if(line.isEmpty())break;

            String[] word = line.split(" ");
            String content = String.join(" ",Arrays.copyOfRange(word,4,word.length));
            Ad ad = new Ad(word[0],word[1],Double.parseDouble(word[2]),Double.parseDouble(word[3]),content);
            ads.add(ad);
//            System.out.println(ad);
        }

    }

    List<Ad> placeAds(BufferedReader br, int k, PrintWriter pw) throws IOException {
        String line;
        AdRequest ar;
        line=br.readLine();
        String[] word = line.split(" ");
        String keywords = String.join(" ",Arrays.copyOfRange(word,3,word.length));
        ar = new AdRequest(word[0],word[1],Double.parseDouble(word[2]),keywords);
        List<Ad> filtered_ads = this.ads.stream().filter((e)->e.getBidValue()>=ar.getFloorBid()).collect(Collectors.toList());
        Map<Ad,Double> scores = new HashMap<Ad,Double>();
        for(Ad ad:filtered_ads){
            Double totalScore =this.relevanceScore(ad,ar)+ Zad2.x * ad.getBidValue()+ Zad2.y*ad.getCtr();
            scores.put(ad,totalScore);
        }
        List<Ad> sortedByScore = filtered_ads.stream().sorted((a1,a2)->Double.compare(scores.get(a2),scores.get(a1))).limit(k).collect(Collectors.toList());
        Collections.sort(sortedByScore);
        Collections.reverse(sortedByScore);
        pw.println("Top ads for request "+ar.getId()+":");


        for(Ad ad: sortedByScore){
//            System.out.println(ad);
            pw.println(ad);
        }
        return filtered_ads;
    }

}

public class Zad2 {
    public final static double x=5.0;
    public final static double y=100.0;
    public static void main(String[] args) throws IOException {
        AdNetwork network = new AdNetwork();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

        int k = Integer.parseInt(br.readLine().trim());

        if (k == 0) {
            network.readAds(br);
            network.placeAds(br, 1, pw);
        } else if (k == 1) {
            network.readAds(br);
            network.placeAds(br, 3, pw);
        } else {
            network.readAds(br);
            network.placeAds(br, 8, pw);
        }

        pw.flush();
    }
}