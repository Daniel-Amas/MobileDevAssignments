import 'package:flutter/material.dart';

class OrderPlanCard extends StatelessWidget {
  final String date;
  final double targetCost;
  final String selectedItems;

  const OrderPlanCard({
    required this.date,
    required this.targetCost,
    required this.selectedItems,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      elevation: 4,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Date: $date',
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 8),
            Text(
              'Target Cost: \$${targetCost.toStringAsFixed(2)}',
              style: const TextStyle(fontSize: 16, color: Colors.blue),
            ),
            const SizedBox(height: 8),
            Text(
              'Selected Items: $selectedItems',
              style: const TextStyle(fontSize: 14, color: Colors.black87),
            ),
          ],
        ),
      ),
    );
  }
}
