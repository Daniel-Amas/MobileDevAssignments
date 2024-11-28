import 'package:flutter/material.dart';

class CheckboxListItem extends StatelessWidget {
  final String name;
  final double cost;
  final bool isSelected;
  final ValueChanged<bool?> onChanged;

  const CheckboxListItem({
    required this.name,
    required this.cost,
    required this.isSelected,
    required this.onChanged,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return CheckboxListTile(
      title: Text(name),
      subtitle: Text('Cost: \$${cost.toStringAsFixed(2)}'),
      value: isSelected,
      onChanged: onChanged,
    );
  }
}
