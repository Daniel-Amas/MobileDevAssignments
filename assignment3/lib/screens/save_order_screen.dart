import 'package:flutter/material.dart';
import '../db_helper.dart';
import '../widgets/checkbox_list_item.dart';
import '../widgets/input_field.dart';
import '../widgets/custom_button.dart';

class SaveOrderScreen extends StatefulWidget {
  const SaveOrderScreen({super.key});

  @override
  _SaveOrderScreenState createState() => _SaveOrderScreenState();
}

class _SaveOrderScreenState extends State<SaveOrderScreen> {
  TextEditingController targetCostController = TextEditingController();
  TextEditingController dateController = TextEditingController();
  List<Map<String, dynamic>> foodItems = [];
  List<int> selectedItems = [];

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

  void saveOrderPlan() async {
    final date = dateController.text;
    final targetCost = double.tryParse(targetCostController.text) ?? 0.0;
    final selectedItemsString = selectedItems.join(',');

    await DBHelper.saveOrderPlan(date, targetCost, selectedItemsString);
    ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Order plan saved successfully')));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Save Order Plan')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            InputField(
              label: 'Target Cost',
              controller: targetCostController,
              keyboardType: TextInputType.number,
            ),
            InputField(
              label: 'Date (YYYY-MM-DD)',
              controller: dateController,
            ),
            Expanded(
              child: ListView.builder(
                itemCount: foodItems.length,
                itemBuilder: (context, index) {
                  final item = foodItems[index];
                  return CheckboxListItem(
                    name: item['name'],
                    cost: item['cost'],
                    isSelected: selectedItems.contains(item['id']),
                    onChanged: (isSelected) {
                      setState(() {
                        if (isSelected!) {
                          selectedItems.add(item['id']);
                        } else {
                          selectedItems.remove(item['id']);
                        }
                      });
                    },
                  );
                },
              ),
            ),
            CustomButton(
              text: 'Save Plan',
              onPressed: saveOrderPlan,
            ),
          ],
        ),
      ),
    );
  }
}
