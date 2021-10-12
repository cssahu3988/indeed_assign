package com.example.indeed_assign.API;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    public String id;

    @SerializedName("user")
    public User user;

    public Data(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public class User{
        @SerializedName("id")
        public String id;

        @SerializedName("profile_image")
        public ProfileImage profileImage;

        public User(String id, ProfileImage profileImage) {
            this.id = id;
            this.profileImage = profileImage;
        }

        public ProfileImage getProfileImage() {
            return profileImage;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setProfileImage(ProfileImage profileImage) {
            this.profileImage = profileImage;
        }
    }

    public class ProfileImage{
        @SerializedName("small")
        public String small;

        @SerializedName("medium")
        public String medium;

        @SerializedName("large")
        public String large;

        public ProfileImage(String small, String medium, String large) {
            this.small = small;
            this.medium = medium;
            this.large = large;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }
}


