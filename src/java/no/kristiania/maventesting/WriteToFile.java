package no.kristiania.maventesting;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

public class WriteToFile {
    String productName;
    String category;

    public void writeToText(String post){
        FileWriter fWriter = null;
        BufferedWriter writer = null;
        try {
            fWriter = new FileWriter("src/public/api/products/products.txt", true);
            writer = new BufferedWriter(fWriter);
            cleanStrings(post);

            writer.write(getCategory()+": ");

            if (category.equals("Women")) writer.write(getProductName());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanStrings(String post){
        String[] postPayload = post.split("&");
        String productName = postPayload[1].replaceAll("\\+", " ").replaceAll("productName=","");
        String category = postPayload[0].replaceAll("category=","").trim();

        setProductName(productName);
        setCategory(category);
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }
}
