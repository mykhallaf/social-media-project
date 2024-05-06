package com.mycompany.advancedproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Comments {
    private String name;
    private int likeCount;
    private int replies;
    private String date;
    private List<Integer> numofLikesList;

    public Comments(String name, List<Integer> numofLikesList, int replies, String date) {
        this.name = name;
        this.numofLikesList = new ArrayList<>(numofLikesList);
        this.replies = replies;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getNumofLikesList() {
        return numofLikesList;
    }

    public void setNumofLikesList(List<Integer> numofLikesList) {
        this.numofLikesList = new ArrayList<>(numofLikesList);
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addLike(int likeCount) {
        numofLikesList.add(likeCount);
    }

    public void removeLike(int likeCount) {
        if (likeCount >= 0) {
            numofLikesList.remove(likeCount);
        }
    }
    
     public void createComments(Scanner scan){
        System.out.println("Type your comment:");
        String text = scan.nextLine();
        LocalDate date = LocalDate.now(); 

        String post = this.name + ":" + text + "[" + date + "]";
        
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
    
    public void viewComments(){
        System.out.println("Comments:");
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
