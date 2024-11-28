class OrderPlan {
  final int? id;
  final String date;
  final double targetCost;
  final String selectedItems;

  OrderPlan(
      {this.id,
      required this.date,
      required this.targetCost,
      required this.selectedItems});

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'date': date,
      'target_cost': targetCost,
      'selected_items': selectedItems
    };
  }
}
