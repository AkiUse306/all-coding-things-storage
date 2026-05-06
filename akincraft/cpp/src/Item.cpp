#include "Item.h"
#include <algorithm>

Item::Item(const std::string& id, const std::string& name, int count, int maxStack)
    : id(id), name(name), count(count), maxStackSize(maxStack) {}

void Item::addCount(int amount) {
    count = std::min(count + amount, maxStackSize);
}

void Item::removeCount(int amount) {
    count = std::max(0, count - amount);
}

bool Item::canStack(const Item& other) const {
    return this->id == other.id && this->count < this->maxStackSize;
}

// === Container Implementation ===

Container::Container(const std::string& containerName, int slots)
    : slotCount(slots), name(containerName) {
    items.resize(slotCount, nullptr);
}

bool Container::addItem(std::shared_ptr<Item> item) {
    if (!item) return false;
    
    // Try to stack first
    for (auto& existingItem : items) {
        if (existingItem && existingItem->canStack(*item)) {
            int spaceLeft = existingItem->getMaxStackSize() - existingItem->getCount();
            int toAdd = std::min(spaceLeft, item->getCount());
            existingItem->addCount(toAdd);
            item->removeCount(toAdd);
            
            if (item->isEmpty()) {
                return true;
            }
        }
    }
    
    // Find empty slot
    for (int i = 0; i < items.size(); i++) {
        if (items[i] == nullptr) {
            items[i] = item;
            return true;
        }
    }
    
    return false;
}

std::shared_ptr<Item> Container::getItem(int slot) {
    if (slot >= 0 && slot < items.size()) {
        return items[slot];
    }
    return nullptr;
}

void Container::setItem(int slot, std::shared_ptr<Item> item) {
    if (slot >= 0 && slot < items.size()) {
        items[slot] = item;
    }
}

void Container::removeItem(int slot) {
    if (slot >= 0 && slot < items.size()) {
        items[slot] = nullptr;
    }
}
