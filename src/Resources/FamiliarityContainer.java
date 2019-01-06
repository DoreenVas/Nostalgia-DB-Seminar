package Resources;
import java.sql.SQLException;
public class FamiliarityContainer implements Container<Float>{
    private float familiarity;

    public FamiliarityContainer(float familiarity){
        this.familiarity = familiarity;
    }
    @Override
    public Float getValue() {
        return this.familiarity;
    }
}


