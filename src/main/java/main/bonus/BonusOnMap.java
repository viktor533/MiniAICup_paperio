package main.bonus;

import main.common.Coordinate;

import java.util.Objects;

public class BonusOnMap {
    private BonusType bonusType;
    private Coordinate position;

    public BonusOnMap(BonusType bonusType, Coordinate position) {
        this.bonusType = bonusType;
        this.position = position;
    }

    public boolean equals(String type, int x, int y) {
        return ((bonusType.equals(BonusType.fromString(type))) && (position.equals(x, y)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(bonusType, position);
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
