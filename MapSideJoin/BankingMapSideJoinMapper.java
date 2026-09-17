import java.io.*;
import java.util.HashMap;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class BankingMapSideJoinMapper
        extends Mapper<LongWritable, Text, Text, Text> {

    private HashMap<String, String> branchMap = new HashMap<>();

    @Override
    protected void setup(Context context)
            throws IOException {

        BufferedReader reader = new BufferedReader(
                new FileReader("branches.csv"));

        String line;

        // Skip header
        reader.readLine();

        while ((line = reader.readLine()) != null) {

            String[] fields = line.split(",");

            String branchID = fields[0];
            String branchName = fields[1];
            String addressID = fields[2];

            branchMap.put(
                branchID,
                branchName + "|" + addressID
            );
        }

        reader.close();
    }

    @Override
    protected void map(
            LongWritable key,
            Text value,
            Context context)
            throws IOException, InterruptedException {

        String line = value.toString();

        // Skip header
        if (line.startsWith("TransactionID")) {
            return;
        }

        String[] fields = line.split(",");

        String transactionID = fields[0];
        String accountOriginID = fields[1];
        String accountDestinationID = fields[2];
        String transactionTypeID = fields[3];
        String amount = fields[4];
        String transactionDate = fields[5];
        String branchID = fields[6];
        String description = fields[7];

        // Look up branch information
        String branchInfo = branchMap.get(branchID);

        if (branchInfo != null) {

            String output =
                    transactionID + "|" +
                    accountOriginID + "|" +
                    accountDestinationID + "|" +
                    transactionTypeID + "|" +
                    amount + "|" +
                    transactionDate + "|" +
                    branchID + "|" +
                    branchInfo + "|" +
                    description;

            context.write(
                    new Text(branchID),
                    new Text(output));
        }
    }
}
