package com.mycompany.advancedproject;
import java.util.ArrayList;
import java.util.List;

public class Posts {
    private String name;
    private String postDate;
    private int numofLikes;
    private int numofShares;
    private int numofComments;
    private List<String> postsList;
    
    //CONSTRUCTOR
    public Posts(String name, String postDate, int numofLikes, int numofShares, int numofComments){
        this.name = name;
        this.postDate = postDate;
        this.numofLikes = numofLikes;
        this.numofShares = numofShares;
        this.numofComments = numofComments;
    }
    
    //SETTERS AND GETTERS
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    
    public void setPostDate(String postDate){
        this.postDate = postDate;
    }
    public String getPostDate(){
        return postDate;
    }    
    
    public void setNumLikes(int numofLikes){
        this.numofLikes = numofLikes;
    }
    public int getNumLikes(){
        return numofLikes;
    }
    
    public void setNumShares(int numofShares){
        this.numofShares = numofShares;
    }
    public int getNumShares(){
        return numofShares;
    }
    
    public void setNumComments(int numofComments){
        this.numofComments = numofComments;
    }
    public int getNumComments(){
        return numofComments;
    }
    
    //METHOD TO DISPLAY POST
    public void DisplayPost(){
        System.out.println("Username: " +name);
        System.out.println("Date: " +postDate);
        System.out.println("Username: " +name);
        System.out.println("Likes: " +numofLikes);
        System.out.println("Comments: " +numofComments);
        System.out.println("Shares: " +numofShares);
    }

    //METHOD TO ADD LIKES, COMMENTS, SHARES
    public void addNumLikes(){
        numofLikes++;
    } 
    public void addNumComment(){
        numofComments++;
    }
    public void addNumShares(){
        numofShares++;
    }
    
    //METHOD TO CREATE POST 
    public void CreatPosts(String createPost){
        postsList.add(createPost);
    }
    
    //METHOD TO REMOVE POST
    public void RemovePost(int postIndex){
        if(postIndex >= 0 && postIndex < postsList.size()){
            postsList.remove(postIndex);
        }
    }
    //METHOD TO EDIT POST
    public void EditPost(int postIndex, String NewPost){
        if(postIndex >= 0 && postIndex < postsList.size()){
            postsList.set(postIndex, NewPost);   
        }
    }
}