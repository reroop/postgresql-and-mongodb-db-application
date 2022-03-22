package com.dbapplication.models.mongo.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "kasutajakonto")
public class UserAccount {

    @Getter
    @Id
    private String _id;

    @Getter
    private String isik_id;

    @Getter
    @Setter
    private String parool;

    @Getter
    @Setter
    private Boolean on_aktiivne;

    @Override
    public String toString() {
        return "UserAccount{" +
                "_id='" + _id + '\'' +
                ", isik_id='" + isik_id + '\'' +
                ", parool='" + parool + '\'' +
                ", on_aktiivne=" + on_aktiivne +
                '}';
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection = "kasutajakonto")
    public static class UserAccountDbEntry {
        @Getter
        @Id
        private String _id;

        @Getter
        private ObjectId isik_id;

        @Getter
        @Setter
        private String parool;

        @Getter
        @Setter
        private Boolean on_aktiivne;

        public UserAccountDbEntry(String personId, String password, Boolean isActive) {
            this.isik_id = new ObjectId(personId);
            this.parool = password;
            this.on_aktiivne = isActive;
        }
    }
}
