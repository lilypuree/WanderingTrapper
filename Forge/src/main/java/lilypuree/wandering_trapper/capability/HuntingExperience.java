package lilypuree.wandering_trapper.capability;

public class HuntingExperience implements IHuntingExperience {

    private int experience = 0;

    @Override
    public void add(int experience) {
        this.experience += experience;
    }

    @Override
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public void copyFrom(IHuntingExperience huntingExperience) {
        this.experience = huntingExperience.getExperience();
    }
}
