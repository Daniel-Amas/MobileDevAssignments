import 'package:flutter/material.dart';
import '../db_helper.dart';
import '../widgets/food_item_card.dart';

class FoodListScreen extends StatefulWidget {
  const FoodListScreen({super.key});

  @override
  _FoodListScreenState createState() => _FoodListScreenState();
}

class _FoodListScreenState extends State<FoodListScreen> {
  List<Map<String, dynamic>> foodItems = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    loadFoodItems();
  }

  Future<void> loadFoodItems() async {
    try {
      final items = await DBHelper.fetchFoodItems();
      print("Items loaded: $items");
      setState(() {
        foodItems = items;
        isLoading = false;
      });
    } catch (e) {
      print("Error loading food items: $e");
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Food Items')),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : foodItems.isEmpty
              ? const Center(child: Text('No food items available.'))
              : ListView.builder(
                  itemCount: foodItems.length,
                  itemBuilder: (context, index) {
                    final item = foodItems[index];
                    return FoodItemCard(
                      name: item['name'],
                      cost: item['cost'],
                    );
                  },
                ),
    );
  }
}
