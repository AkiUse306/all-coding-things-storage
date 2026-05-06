#pragma once

#include <string>
#include <vector>
#include <memory>

/**
 * Item representation
 */
class Item {
private:
    std::string id;
    std::string name;
    int count;
    int maxStackSize;
    
public:
    Item(const std::string& id, const std::string& name, int count = 1, int maxStack = 64);
    
    void addCount(int amount);
    void removeCount(int amount);
    bool canStack(const Item& other) const;
    
    std::string getId() const { return id; }
    std::string getName() const { return name; }
    int getCount() const { return count; }
    int getMaxStackSize() const { return maxStackSize; }
    bool isEmpty() const { return count <= 0; }
};

/**
 * Container/chest for storing items
 */
class Container {
private:
    std::vector<std::shared_ptr<Item>> items;
    int slotCount;
    std::string name;
    
public:
    Container(const std::string& containerName, int slots);
    
    bool addItem(std::shared_ptr<Item> item);
    std::shared_ptr<Item> getItem(int slot);
    void setItem(int slot, std::shared_ptr<Item> item);
    void removeItem(int slot);
    
    std::vector<std::shared_ptr<Item>> getItems() const { return items; }
    std::string getName() const { return name; }
    int getSlotCount() const { return slotCount; }
};
