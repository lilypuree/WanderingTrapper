package lilypuree.wandering_trapper.capability;

public interface IHuntingExperience {
    public void add(int experience);

    public void setExperience(int experience);

    public int getExperience();

    public void copyFrom(IHuntingExperience huntingExperience);
}
