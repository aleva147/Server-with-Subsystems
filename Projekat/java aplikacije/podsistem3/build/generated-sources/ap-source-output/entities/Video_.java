package entities;

import entities.Gledanje;
import entities.Ocena;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-06-19T21:32:25")
@StaticMetamodel(Video.class)
public class Video_ { 

    public static volatile ListAttribute<Video, Gledanje> gledanjeList;
    public static volatile ListAttribute<Video, Ocena> ocenaList;
    public static volatile SingularAttribute<Video, Integer> id;

}