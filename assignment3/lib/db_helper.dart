import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

class DBHelper {
  static Database? _database;

  // Get database instance
  static Future<Database> getDatabase() async {
    if (_database != null) return _database!;
    final dbPath = await getDatabasesPath();
    _database = await openDatabase(
      join(dbPath, 'food_ordering.db'),
      version: 1,
      onCreate: (db, version) async {
        print("Creating database...");
        await db.execute(
            'CREATE TABLE food_items (id INTEGER PRIMARY KEY, name TEXT, cost REAL)');
        await db.execute(
            'CREATE TABLE order_plans (id INTEGER PRIMARY KEY, date TEXT, target_cost REAL, selected_items TEXT)');
      },
    );

    // Always ensure food items are preloaded
    await _insertDefaultFoodItems(_database!);
    return _database!;
  }

  // Create database and preload food items
  static Future<void> _createDatabase(Database db, int version) async {
    print("Creating database...");
    await db.execute(
        'CREATE TABLE food_items (id INTEGER PRIMARY KEY, name TEXT, cost REAL)');
    await db.execute(
        'CREATE TABLE order_plans (id INTEGER PRIMARY KEY, date TEXT, target_cost REAL, selected_items TEXT)');

    print("Preloading food items...");
    await _insertDefaultFoodItems(db);
  }

  // Preload 20 food items
  static Future<void> _insertDefaultFoodItems(Database db) async {
    // Check if the food_items table is empty
    final existingItems = await db.query('food_items');
    if (existingItems.isNotEmpty) {
      print("Food items already exist. Skipping preload.");
      return;
    }

    print("Preloading food items...");
    const foodItems = [
      {'name': 'Pizza', 'cost': 12.5},
      {'name': 'Burger', 'cost': 8.0},
      {'name': 'Pasta', 'cost': 10.0},
      {'name': 'Sandwich', 'cost': 6.5},
      {'name': 'Salad', 'cost': 5.0},
      {'name': 'Soup', 'cost': 4.5},
      {'name': 'Sushi', 'cost': 15.0},
      {'name': 'Taco', 'cost': 7.5},
      {'name': 'Fried Rice', 'cost': 9.0},
      {'name': 'Chicken Wings', 'cost': 11.0},
      {'name': 'Steak', 'cost': 18.0},
      {'name': 'Ice Cream', 'cost': 3.5},
      {'name': 'Pancakes', 'cost': 7.0},
      {'name': 'Waffles', 'cost': 8.0},
      {'name': 'Coffee', 'cost': 2.5},
      {'name': 'Tea', 'cost': 2.0},
      {'name': 'Smoothie', 'cost': 4.0},
      {'name': 'Donut', 'cost': 1.5},
      {'name': 'Bagel', 'cost': 2.0},
      {'name': 'Muffin', 'cost': 2.5},
    ];

    for (var item in foodItems) {
      print("Inserting ${item['name']}...");
      await db.insert('food_items', item);
    }
    print("Preloading complete.");
  }

  // Query all food items
  static Future<List<Map<String, dynamic>>> fetchFoodItems() async {
    final db = await getDatabase();
    final items = await db.query('food_items');
    print("Fetched food items: $items");
    return items;
  }

  // Insert a new food item
  static Future<void> insertFoodItem(String name, double cost) async {
    final db = await getDatabase();
    await db.insert('food_items', {'name': name, 'cost': cost});
  }

  // Delete a food item
  static Future<void> deleteFoodItem(int id) async {
    final db = await getDatabase();
    await db.delete('food_items', where: 'id = ?', whereArgs: [id]);
  }

  // Update a food item
  static Future<void> updateFoodItem(int id, String name, double cost) async {
    final db = await getDatabase();
    await db.update('food_items', {'name': name, 'cost': cost},
        where: 'id = ?', whereArgs: [id]);
  }

  // Save order plan
  static Future<void> saveOrderPlan(
      String date, double targetCost, String selectedItems) async {
    final db = await getDatabase();
    await db.insert('order_plans', {
      'date': date,
      'target_cost': targetCost,
      'selected_items': selectedItems,
    });
  }

  // Query an order plan by date
  static Future<Map<String, dynamic>?> fetchOrderPlan(String date) async {
    final db = await getDatabase();
    final results =
        await db.query('order_plans', where: 'date = ?', whereArgs: [date]);
    return results.isNotEmpty ? results.first : null;
  }
}
