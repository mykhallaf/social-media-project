package com.mycompany.advancedproject;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.io.FileReader;
import java.io.BufferedReader;


public class Posts {
    private String username;
    private String postDate;
    private List<Integer> numLikesList;
    private int numShares;
    private int numComments;
    private String content;
    

    // CONSTRUCTOR
    public Posts (String username, String postDate) {
        this.username = username;
        this.postDate = postDate;
        this.numLikesList = new ArrayList<>();
        this.numShares = 0;
        this.numComments = 0;
    }

    // GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public List<Integer> getNumLikesList() {
        return numLikesList;
    }

    public void setNumLikesList(List<Integer> numLikesList) {
        this.numLikesList = numLikesList;
    }
    
    public void addLike(int likeCount) {
        numLikesList.add(likeCount);
    }
    
    public int getNumShares() {
        return numShares;
    }

    public void incrementShares() {
        this.numShares++;
    }

    public int getNumComments() {
        return numComments;
    }

    public void incrementComments() {
        this.numComments++;
    }

    public void createPost(Scanner scan){
        System.out.println("Create your post:");
        String text = scan.nextLine();
        LocalDate date = LocalDate.now(); 

        String post = this.username + ":" + text + "[" + date + "]";
        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Notepad++\\test\\post.txt", true));
            writer.write(post);
            writer.newLine();
            writer.close();
            System.out.println("Upload Successful");
        }
        catch(IOException e){
            System.out.println("Error Occured");
        }
    
    }
    
    public void viewPosts(){
        System.out.println("Feed:");
        try{
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Notepad++\\test\\post.txt"));
            String s;
            while((s = reader.readLine()) != null){
                System.out.println(s);
            }
        }    
        catch(IOException e){
            System.out.println("Error");
        }   
    }
}