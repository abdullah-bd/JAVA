/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minsubstring;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author spark
 */
public class MinSubString {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mw am = new Mw();
        String ds = am.minWindow("ahffaksfajeeubsne", "jefaa");
        System.out.println(ds);
    }

    ///////////////////////////////////////////////////////////////////////////////////
}

class Mw {

    public String minWindow(String searchString, String t) {

        // The characters a satisfiable window must cover mapped to their frequency
        Map<Character, Integer> requiredCharacters = buildMappingOfCharactersToOccurrences(t);

        /*
    For our window. Map all characters in the window to their occurrence count. You
    will see how we use this below.
         */
        Map<Character, Integer> windowCharacterMapping = new HashMap<>();

        /*
    2 pointers. Left holds the left index of the window we are inspecting and right
    holds the right index.
    The approach is simple. We keep moving right (don't touch left) until the window
    we hold satisfies all required characters. Then we take note whether the window
    we see beats the smallest satisfiable window we have found so far.
    We then contract the left pointer in while the window still satisfies all required
    characters (at every point continuing to check if we have beaten the smallest window
    ever seen to this point)
    As soon as the window no longer satisfies, go back to expanding the right pointer.
    We are finished when the right pointer runs over the array because we can't keep
    expanding the window to satisfy at that point.
         */
        int left = 0;
        int right = 0;

        /*
    'totalCharFrequenciesToMatch' is the total characters we need to match frequency for
    in the window. If I have 1 'a' in my window and I need 2 'a' chars...then the char
    frequencies don't match.
    'charFrequenciesInWindowThatMatch' is the count of frequencies that we have satisfied.
    When 'totalCharFrequenciesToMatch' == 'charFrequenciesInWindowThatMatch' then it can be
    said that the current window satisfies that property of having all characters with matching
    counts to the string t.
         */
        int totalCharFrequenciesToMatch = requiredCharacters.size();
        int charFrequenciesInWindowThatMatch = 0;

        /*
    We will keep track of the best window we have seen so far
         */
        int minWindowLengthSeenSoFar = Integer.MAX_VALUE;
        String minWindow = "";

        while (right < searchString.length()) {   //lol- loop ghurbe pura string//

            /*
      Add the character on the right pointer to the hashtable that maps the characters seen
      in the window to their occurrence count
             */
            char characterAtRightPointer = searchString.charAt(right);
            addCharacterToHashtableMapping(windowCharacterMapping, characterAtRightPointer);

            /*
      Is this character part of the requirement?
             */
            boolean rightCharIsARequirement = requiredCharacters.containsKey(characterAtRightPointer);
            if (rightCharIsARequirement) {
                /*
        Does the current window frequency match the required frequency?
                 */
                boolean requirementForCharacterMet = requiredCharacters.get(characterAtRightPointer).intValue()
                        == windowCharacterMapping.get(characterAtRightPointer).intValue();
                if (requirementForCharacterMet) {
                    /*
          If so then we have one more frequency requirement that matches...remember when:
          'totalCharFrequenciesToMatch' == 'charFrequenciesInWindowThatMatch' then we know that
          we have a satisfying window
                     */
                    charFrequenciesInWindowThatMatch++;
                }
            }

            /*
      Does this window satisfy? Ok...if it does try contracting the left pointer inward until
      we go over the right pointer.
             */
            while (charFrequenciesInWindowThatMatch == totalCharFrequenciesToMatch && left <= right) {

                /*
        Put these things in plain English so you don't get confused
                 */
                char characterAtLeftPointer = searchString.charAt(left);
                int windowSize = right - left + 1;

                /*
        Have we beat the best satisfiable window seen so far? Ok...if so then update
        the tracking variables
                 */
                if (windowSize < minWindowLengthSeenSoFar) {
                    minWindowLengthSeenSoFar = windowSize;
                    minWindow = searchString.substring(left, right + 1);
                }

                /*
        This character will get contracted out. It won't be in the window anymore once
        left moves forward.
                 */
                windowCharacterMapping.put(characterAtLeftPointer, windowCharacterMapping.get(characterAtLeftPointer) - 1);

                /*
        Was this character part of the requirement? If so then its frequency changing matters to us.
                 */
                boolean leftCharIsARequirement = requiredCharacters.containsKey(characterAtLeftPointer);
                if (leftCharIsARequirement) {
                    /*
          Does the character frequence count not fall below the threshold of satisfying?
                     */
                    boolean characterFailsRequirement = windowCharacterMapping.get(characterAtLeftPointer)
                            < requiredCharacters.get(characterAtLeftPointer);

                    if (characterFailsRequirement) {
                        /*
            If so then we have one less character frequency mapping in the window that matches
                         */
                        charFrequenciesInWindowThatMatch--;
                    }
                }

                /*
        Move the left point forward. We will keep going until the window no longer satisfies.
                 */
                left++;
            }

            /*
      We have moved left as far as it could go. It either led to a window that no longer
      satisfied or left passed the right pointer. Either way...advance the right pointer.
             */
            right++;
        }

        return minWindow;
    }

    private Map<Character, Integer> buildMappingOfCharactersToOccurrences(String s) {

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            int occurrencesOfCharacter = map.getOrDefault(s.charAt(i), 0);
            map.put(s.charAt(i), occurrencesOfCharacter + 1);
        }

        return map;
    }

    private void addCharacterToHashtableMapping(Map<Character, Integer> map, Character c) {
        int occurrences = map.getOrDefault(c, 0);
        map.put(c, occurrences + 1);
    }
}
