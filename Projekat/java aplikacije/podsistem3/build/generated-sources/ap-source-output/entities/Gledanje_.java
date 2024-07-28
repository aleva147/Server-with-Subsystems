package entities;

import entities.Korisnik;
import entities.Video;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-06-19T21:32:25")
@StaticMetamodel(Gledanje.class)
public class Gledanje_ { 

    public static volatile SingularAttribute<Gledanje, Video> idVid;
    public static volatile SingularAttribute<Gledanje, Korisnik> idKor;
    public static volatile SingularAttribute<Gledanje, Date> datumvreme;
    public static volatile SingularAttribute<Gledanje, Integer> trajanje;
    public static volatile SingularAttribute<Gledanje, Integer> id;
    public static volatile SingularAttribute<Gledanje, Integer> pocetak;

}