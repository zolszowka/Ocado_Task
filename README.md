# Basket Splitter

The Basket Splitter is a Java library for splitting a list of items into different delivery methods based on their associated products' delivery preferences.

## Installation

To install the Fat Jar containing the BasketSplitter as a Maven dependency, you can follow these steps:

1. Install the JAR file to your local Maven repository using the following command:
```
mvn install:install-file -Dfile=<Path to BasketSplitter.jar> -DgroupId=com.ocado.basket -DartifactId=BasketSplitter -Dversion=1.0 -Dpackaging=jar
```
Replace <Path to BasketSplitter.jar> with the actual path to your BasketSplitter.jar file.

2. Once the JAR is installed, you can add it as a dependency in your Maven project's pom.xml file:
```
<dependency>
    <groupId>com.ocado.basket</groupId>
    <artifactId>BasketSplitter</artifactId>
    <version>1.0</version>
</dependency>
```

## Usage Example

The following code creates an instance of BasketSplitter with the provided configuration file, then splits the product list into different shipping methods, and finally prints the split products for each shipping method.
```java
String absolutePathToConfigFile = "/path/to/config.json"
List<String> items = Arrays.asList("Chocolate - Unsweetened", "Peach - Fresh", "Sauce - Salsa", "Bread - Petit Baguette")

BasketSplitter basketSplitter = new BasketSplitter(configFile)
Map<String, List<String>> splittedProducts = basketSplitter.split(items)

for (Map.Entry<String, List<String>> entry : splittedProducts.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            System.out.println("Delivery method " + key + " contains products: " + value);
}
```

## How The Algorithm Works

The algorithm implemented in the BasketSplitter class follows these steps:
1. **Initialization:** Initialize a `deliveryMethod` map to count occurences of each delivery method and a `result` map to store split products by delivery method.
2. **Loading Delivery Methods:** For each item in the given list, load delivery methods from a JSON file and increment occurences for each delivery method.
3. **Splitting Products:** While there are products to split:
   * Choose the delivery method with the highest occurences.
   * Iterating through the products and checking if a product can be delivered using the selected delivery method. If so, the product is added to the corresponding delivery method in the `result` map, removed from the `productsToSplit` list, and the occurrence count is updated in the `deliveryMethods` map.
   * Removing the selected delivery method from the `deliveryMethods` map.
4. **Return Result:** Return products grouped by delivery method.

## Classes
### BasketSplitter Class

The `BasketSplitter` class is responsible for splitting a list of items into different delivery methods.

### Product Class

The `Product` class represents a product that can be delivered by one or more delivery methods.

### JSONLoader Class

The `JSONLoader` class is responsible for loading delivery methods of a given product from a JSON file.

