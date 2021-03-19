package text_editor;

public class Recursion extends Algorithm {

    @Override
    public String reverseSentence(String sentence) {
        StringBuilder result = new StringBuilder();
        return result.append(sentence).append(" ").append(reverse(sentence)).toString();
    }

    public String reverse(String sentence) {
        StringBuilder builder = new StringBuilder(sentence);
        builder.append("\n");
        if (sentence.isEmpty())
            return builder.append(sentence).toString();

        return reverse(sentence.substring(1)) + sentence.charAt(0);
    }
}
