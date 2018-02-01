/*
A simple class just needed to hold information related to a place (so populations from multiple zip codes with the same
city and state can be added).
*/
class Place {

    String cityState;
    String zipCode;
//    public String name;
//    public String state;
    int population;
    int taxReturns;

    Place(String cityState, String zipCode, int population, int taxReturns) {
        this.cityState = cityState;
        this.zipCode = zipCode;
//        this.name = name;
//        this.state = state;
        this.population = population;
        this.taxReturns = taxReturns;
    }


}