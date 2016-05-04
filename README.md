[Konf Main Website](http://advantageous.github.io/konf/)

## Config Lib to combine Boon JSON (LAX and normal) with Konf

This lib allows you to get type safe config from JSON LAX, and JSON files.


Let's look at some examples.

## Combine BOON JSON with Konf strongly typed config

This project allows you to combine Boon JSON parser with Konf.

#### Combining Boon JSON with Konf

```java
import static io.advantageous.konf.boon.BoonConfigLoader.boonConfig;
...

        final Config config = boonConfig("reference.conf");
        final String abc = config.getString("abc");
        assertEquals("abc", abc);

```

Now let's read some strict JSON.

#### Reading Strict JSON
```java

import static io.advantageous.konf.boon.BoonConfigLoader.loadStrictJson;
...

        final Config config = loadStrictJson("test-config.json");
        final String bar = config.getString("bar");
        assertEquals("baz", bar);

        final int foo = config.getInt("foo");
        assertEquals(1, foo);
```

We can combine JSON Lax with Konf as a fallback (or the other way around).

#### Combining Boon JSON Lax with Konf Config.
```java

import static io.advantageous.config.ConfigLoader.configs;
import static io.advantageous.config.ConfigLoader.config;


Config config = configs(boonConfig("reference.conf"),  //lax JSON
                        config("test-config.js")); // test-config

```

## Lax JSON

Boon supports strict JSON or lax JSON as in relaxed JSON.
With relaxed JSON you can single quote keys or use no quote at all.
Relaxed JSON is very similar to Type Safe Config format.

You can use `=` instead of `:`.
You can use all three common forms of comments `#`, `\*`, and `\\`.

Add an extra comma, forget a comma, and it still works.

#### ReLax JSON example

```javascript
{
  abc : "abc",

  //This allows comments
  myUri: "http://host:9000/path?foo=bar",

  # There are different ways to allow comments
  someKey: {
    nestedKey: 234,
    other: "this text"
  },

  /*
    Are commnents great?
  */

  int1: 1,

  //Look no comma
  float1 : 1.0

  //Look I can use equal instead of colon
  double1 = 1.0
  long1: 1,
  
  //No quote around the string
  string1: rick,
  
  //Don't need to quote strings but you can.
  stringList: [Foo, Bar],
  configInner: {
    int2: 2,
    float2: 2.01
  },
  myClass: "java.lang.Object",
  myURI: "http://localhost:8080/foo",
  //Maps can have single quotes or double quotes
  employee: {'id': 123, name: "Geoff"},
  employees: [
    {id: 123, "name": "Geoff"},
    {id: 456, "name": "Rick"},
    {id: 789, 'name': "Paul"}
  ],
  //commas are optional
  floats: [1.0, 2.0, 3.0]
  doubles: [1.0, 2.0, 3.0],
  longs: [1.0, 2.0, 3.0],
  ints: [1, 2, 3],


  //You can leave off the comma
  diskSpace: "10gigabytes"

  // You can end in a comma makes cutting and pasting easier since you 
  // don't have to worry about the trailing comma.
  diskVolumes: ['10 gigabytes', '10GB', '10gigabytes', '10B'],
}
```

Keep in mind that you do not have to use Lax JSON. 
Lax JSON is much more terse, and allows comments.


## Good parse error messages

Boon gives good parse error messages, and it will tell you the exact line
of config that has the problem.

Example

#### Forgot a comma while doing strict JSON
```javascript
{
  "foo" : 1
  "bar" : "baz"
}
```

#### Hey you have a problem right on line 3 
```
The current character read is '"' with an int value of 34
expecting '}' or ',' but got current char '"' with an int value of 34
line number 3
index number 16
  "bar" : "baz"
..^
```
