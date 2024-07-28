package entities;

import entities.Korisnik;
import entities.OcenaPK;
import entities.Video;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-06-19T21:32:25")
@StaticMetamodel(Ocena.class)
public class Ocena_ { 

    public static volatile SingularAttribute<Ocena, Date> datumvreme;
    public static volatile SingularAttribute<Ocena, OcenaPK> ocenaPK;
    public static volatile SingularAttribute<Ocena, Video> video;
    public static volatile SingularAttribute<Ocena, Integer> ocena;
    public static volatile SingularAttribute<Ocena, Korisnik> korisnik;

}