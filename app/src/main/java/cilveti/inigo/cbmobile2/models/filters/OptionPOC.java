package cilveti.inigo.cbmobile2.models.filters;

import java.util.List;

public class OptionPOC {

    public static final int CHOICE_MULTIPLE = 1;
    public static final int CHOICE_UNIQUE = 2;
    public static final int CHOICE_INPUT = 3;

    private boolean setsCode;
    private boolean setsValue;
    private int choiceType;
    private int code;
    private List<OptionPOC> optionsContinue;
    private List<String> optionsDecide;


}
