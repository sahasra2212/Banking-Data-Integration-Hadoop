import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class BankingJoinReducer
        extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        String customerData = null;

        // One customer can have multiple accounts
        java.util.List<String> accountDataList =
                new java.util.ArrayList<>();

        // Separate CUSTOMER and ACCOUNT records
        for (Text value : values) {

            String data = value.toString();

            if (data.startsWith("CUSTOMER|")) {
                customerData = data.substring("CUSTOMER|".length());
            }

            else if (data.startsWith("ACCOUNT|")) {
                accountDataList.add(
                        data.substring("ACCOUNT|".length())
                );
            }
        }

        // Perform the join
        if (customerData != null) {

            for (String accountData : accountDataList) {

                String joinedRecord =
                        customerData + "|" + accountData;

                context.write(key, new Text(joinedRecord));
            }
        }
    }
}
