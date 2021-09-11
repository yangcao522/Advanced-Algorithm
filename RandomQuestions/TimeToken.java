package DoorDash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
Problem Statement
To find all the stores that are open in a user’s delivery radius, we need to check the store’s operating hours. We currently store this information in Elasticsearch but the query performance for our use-case is turning out to be not very efficient. The format that we store it in Elasticsearch is something like this:

{
  "store": {
    "store_name": "Tasty Pizzas",
    "store_id": "123345",
    "operating_hours": [
      {
        "day": "mon",
        "hours.open": "10:00 am",
        "hours.close": "2:00 pm"
      },
      {
        "day": "tue",
        "hours.open": "10:00 am",
        "hours.close": "2:00 pm"
      },
      {
        "day": "wed",
        "hours.open": "10:00 am",
        "hours.close": "2:00 pm"
      },
      {
        "day": "thu",
        "hours.open": "10:00 am",
        "hours.close": "2:00 pm"
      },
      {
        "day": "fri",
        "hours.open": "10:00 am",
        "hours.close": "2:00 pm"
      },
      {
        "day": "sat",
        "hours.open": "10:00 am",
        "hours.close": "2:00 pm"
      }
    ]
  }
}
We want to experiment to improve the performance of fetching open stores by converting the operating hours into encoded tokens which would be of fixed length of 5. The first digit would represent the day, the next 4 digits would represent the hours in 24 hours format.

Monday maps to number 1, Tuesday to number 2 and so on.

Ex: Monday, 10:00am transforms to 11000 Ex: Monday, 2:00pm transforms to 11400 (as 2pm -> 14:00 in 24 hr format)

Write a function that takes in a start_time and end_time and gives back a li‍‌‌‌‌‍‍‍‌‍‌‌‍‌‌‌‍‌‌st of encoded tokens. Ensure you validate the input Note: We round up the time to next 5 mins

Ex: Input: ("mon 10:00 am", "mon 11:00 am")
Output: [“11000”, “11005”, “11010”, “11015”, “11020”, “11025”, “11030”, “11035”, “11040”, “11045”, “11050”, “11055”, “11100”]
          11000    11005    11010    11015    11020    11025    11030    11035    11040    11045    11050    11055    11100

 Input: ("mon 10:15 am", "mon 11:00 am")
 Output: [“11015”, “11020”, “11025”, “11030”, “11035”, “11040”, “11045”, “11050”, “11055”, “11100”]
           11015    11020    11025    11030    11035    11040    11045    11050    11055    11100
*/
public class TimeToken {
    static class Token {
        int day;
        int mins;
        public Token(int day, int mins) {
            this.day = day;
            this.mins = mins;
        }

        @Override
        public String toString() {
            return String.format("%d%02d%02d", day, mins / 60, mins % 60);
        }
    }

    public static List<String> convertToTokens(String start, String end) {
        Map<String, Integer> map = new HashMap<>();
        map.put("mon", 1);
        map.put("tue", 1);
        map.put("wed", 1);
        map.put("thu", 1);
        map.put("fri", 1);
        map.put("sat", 1);
        map.put("sun", 1);


        Token s = convertTime2Token(start, map);
        Token e = convertTime2Token(end, map);
        List<String> ans = new ArrayList<>();

        while (s.day * 24 * 60 + s.mins <= e.day * 24 * 60 + e.mins) {
            ans.add(s.toString());
            s.mins += 5;
        }

        return ans;
    }

    private static Token convertTime2Token(String time, Map<String, Integer> map) {
        String[] strs = time.split(" ");
        // get days
        int day = map.get(strs[0]);

        // am or pm
        boolean am = "am".equals(strs[2]);
        int base = am ? 0 : 12 * 60;

        //convert hhmm -> mins
        String[] hhmm = strs[1].split(":");
        int hour = Integer.valueOf(hhmm[0]);
        int mins = hour * 60 + base + Integer.valueOf(hhmm[1]);

        //round
        int roundedMins = (int) Math.ceil(1.0 * mins / 5) * 5;
        return new Token(day, roundedMins);
    }

    public static void main(String[] args) {
        List<String> ans = convertToTokens("mon 10:09 am", "mon 11:00 am");
        for (String str : ans) {
            System.out.print(str + " ");
        }

    }
}
