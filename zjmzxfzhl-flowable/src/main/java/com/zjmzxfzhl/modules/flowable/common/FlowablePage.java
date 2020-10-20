package com.zjmzxfzhl.modules.flowable.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@SuppressWarnings({"rawtypes"})
public class FlowablePage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询数据列表
     */
    private List records = Collections.emptyList();
    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;
    /**
     * 当前页
     */
    private int current = 1;

    private List<Order> orders;

    public FlowablePage() {
    }

    public FlowablePage(int current, int size) {
        this.current = current;
        this.size = size;
    }

    public FlowablePage(int current, int size, List<Order> orders) {
        super();
        this.current = current;
        this.size = size;
        this.orders = orders;
    }

    public static FlowablePage of(int current, int size) {
        return new FlowablePage(current, size);
    }

    public static FlowablePage of(int current, int size, List<Order> orders) {
        return new FlowablePage(current, size, orders);
    }

    public List getRecords() {
        return records;
    }

    public void setRecords(List records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public long getOffset() {
        return (long) current * (long) size;
    }

    public static class Order implements Serializable {
        private static final long serialVersionUID = 1L;
        private String property;
        private Direction direction;

        public Order(String property, Direction direction) {
            super();
            this.property = property;
            this.direction = direction;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }
    }

    public static enum Direction {
        /**
         * 排序类型
         */
        ASC, DESC;

        public boolean isAscending() {
            return this.equals(ASC);
        }

        public boolean isDescending() {
            return this.equals(DESC);
        }

        public static Direction fromString(String value) {

            try {
                return Direction.valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be " +
                        "either 'desc' or 'asc' (case insensitive).", value), e);
            }
        }

        public static Optional<Direction> fromOptionalString(String value) {
            try {
                return Optional.of(fromString(value));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }
}
