import 'package:flutter/material.dart';
import '../db_helper.dart';
import '../widgets/input_field.dart';
import '../widgets/custom_button.dart';

class ManageScreen extends StatefulWidget {
  const ManageScreen({super.key});

  @override
  _ManageScreenState createState() => _ManageScreenState();
}

class _ManageScreenState extends State<ManageScreen> {
  List<Map<String, dynamic>> foodItems = [];
  TextEditingController nameController = TextEditingController();
  TextEditingController costController = TextEditingController();

  @override
  void initState() {
    super.initState();
    loadFoodItems();
  }

  Future<void> loadFoodItems() async {
    final items = await DBHelper.fetchFoodItems();
    setState(() {
      foodItems = items;
    });
  }

  void addFoodItem() async {
    final name = nameController.text;
    final cost = double.tryParse(costController.text) ?? 0.0;
    await DBHelper.insertFoodItem(name, cost);
    nameController.clear();
    costController.clear();
    loadFoodItems();
  }

  void deleteFoodItem(int id) async {
    await DBHelper.deleteFoodItem(id);
    loadFoodItems();
  }

  void updateFoodItem(int id) async {
    final name = nameController.text;
    final cost = double.tryParse(costController.text) ?? 0.0;
    await DBHelper.updateFoodItem(id, name, cost);
    nameController.clear();
    costController.clear();
    loadFoodItems();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Manage Food Items')),
      body: Column(
        children: [
          InputField(
            label: 'Name',
            controller: nameController,
          ),
          InputField(
            label: 'Cost',
            controller: costController,
            keyboardType: TextInputType.number,
          ),
          CustomButton(
            text: 'Add Item',
            onPressed: addFoodItem,
          ),
          Expanded(
            child: ListView.builder(
              itemCount: foodItems.length,
              itemBuilder: (context, index) {
                final item = foodItems[index];
                return Card(
                  margin: const EdgeInsets.symmetric(
                      vertical: 8.0, horizontal: 16.0),
                  elevation: 4,
                  child: ListTile(
                    title: Text(item['name']),
                    subtitle: Text('Cost: \$${item['cost']}'),
                    trailing: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        IconButton(
                          icon: const Icon(Icons.edit, color: Colors.blue),
                          onPressed: () {
                            nameController.text = item['name'];
                            costController.text = item['cost'].toString();
                            updateFoodItem(item['id']);
                          },
                        ),
                        IconButton(
                          icon: const Icon(Icons.delete, color: Colors.red),
                          onPressed: () => deleteFoodItem(item['id']),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
