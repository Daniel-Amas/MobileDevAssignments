import 'package:flutter/material.dart';
import '../db_helper.dart';
import '../widgets/order_plan_card.dart';
import '../widgets/input_field.dart';
import '../widgets/custom_button.dart';

class QueryOrderScreen extends StatefulWidget {
  const QueryOrderScreen({super.key});

  @override
  _QueryOrderScreenState createState() => _QueryOrderScreenState();
}

class _QueryOrderScreenState extends State<QueryOrderScreen> {
  TextEditingController dateController = TextEditingController();
  Map<String, dynamic>? orderPlan;

  void queryOrderPlan() async {
    final date = dateController.text;
    final plan = await DBHelper.fetchOrderPlan(date);
    setState(() {
      orderPlan = plan;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Query Order Plan')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            InputField(
              label: 'Date (YYYY-MM-DD)',
              controller: dateController,
            ),
            CustomButton(
              text: 'Query Plan',
              onPressed: queryOrderPlan,
            ),
            if (orderPlan != null)
              OrderPlanCard(
                date: orderPlan!['date'],
                targetCost: orderPlan!['target_cost'],
                selectedItems: orderPlan!['selected_items'],
              ),
          ],
        ),
      ),
    );
  }
}
