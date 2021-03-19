package text_editor;

public class Iterate extends Algorithm {

    @Override
    public String reverseSentence(String str) {
        StringBuilder reversed = new StringBuilder(str);
        reversed.append("\n");
        for (int i = str.length() - 1; i >= 0; i--)  {
            reversed.append(str.charAt(i));
        }
        return reversed.toString();
    }
}
