import 'package:flutter/material.dart';
import 'food_list_screen.dart';
import 'save_order_screen.dart';
import 'query_order_screen.dart';
import 'manage_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Food Ordering App')),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => FoodListScreen()));
              },
              child: const Text('View Food Items'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => SaveOrderScreen()));
              },
              child: const Text('Save Order Plan'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => QueryOrderScreen()));
              },
              child: const Text('Query Order Plan'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => ManageScreen()));
              },
              child: const Text('Manage Food Items'),
            ),
          ],
        ),
      ),
    );
  }
}
