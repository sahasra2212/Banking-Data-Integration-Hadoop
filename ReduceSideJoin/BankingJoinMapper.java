import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class BankingJoinMapper
        extends Mapper<LongWritable, Text, Text, Text> {

    private Text outputKey = new Text();
    private Text outputValue = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString().trim();

        // Ignore empty lines
        if (line.isEmpty()) {
            return;
        }

        // Identify which CSV file the record came from
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fileName = fileSplit.getPath().getName();

        // Ignore headers
        if (line.startsWith("CustomerID,") ||
            line.startsWith("AccountID,")) {
            return;
        }

        String[] fields = line.split(",", -1);

        // -----------------------------
        // CUSTOMER RECORD
        // -----------------------------
        if (fileName.equals("customers.csv")) {

            String customerID = fields[0];

            String customerData =
                    "CUSTOMER|" +
                    fields[1] + "|" +
                    fields[2] + "|" +
                    fields[3] + "|" +
                    fields[4] + "|" +
                    fields[5];

            outputKey.set(customerID);
            outputValue.set(customerData);

            context.write(outputKey, outputValue);
        }

        // -----------------------------
        // ACCOUNT RECORD
        // -----------------------------
        else if (fileName.equals("accounts.csv")) {

            String customerID = fields[1];

            String accountData =
                    "ACCOUNT|" +
                    fields[0] + "|" +
                    fields[2] + "|" +
                    fields[3] + "|" +
                    fields[4] + "|" +
                    fields[5];

            outputKey.set(customerID);
            outputValue.set(accountData);

            context.write(outputKey, outputValue);
        }
    }
}
