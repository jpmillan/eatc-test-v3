-- Enable UUID extension for primary keys
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Create Restaurant Table
CREATE TABLE restaurant (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    address1 VARCHAR(255),
    suburb VARCHAR(100),
    state VARCHAR(50),
    postcode VARCHAR(20),
    open_time TIME,
    close_time TIME,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. Create Deal Table
CREATE TABLE deal (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    restaurant_id UUID NOT NULL,
    discount_percentage INTEGER NOT NULL CHECK (discount_percentage > 0 AND discount_percentage <= 100),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_dine_in BOOLEAN DEFAULT TRUE,
    is_lightning BOOLEAN DEFAULT FALSE,
    quantity_available INTEGER DEFAULT 10 CHECK (quantity_available >= 0),
    active_date DATE DEFAULT CURRENT_DATE,
    metadata JSONB, -- Flexible column for extra attributes
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign Key Constraint
    CONSTRAINT fk_restaurant
        FOREIGN KEY (restaurant_id) 
        REFERENCES restaurant(id)
        ON DELETE CASCADE,
        
    -- Check Constraint: End time must be after start time (handling midnight crossing requires application logic or complex constraints)
    CONSTRAINT check_time_order CHECK (end_time > start_time)
);

-- 3. Create Indexes for Performance

-- Index for searching deals by time (Vital for Task 1)
CREATE INDEX idx_deal_time_window ON deal (start_time, end_time);

-- Index for finding all deals for a specific restaurant
CREATE INDEX idx_deal_restaurant_id ON deal (restaurant_id);
-- 1. Create Restaurant Table
CREATE TABLE restaurant (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    address1 VARCHAR(255),
    suburb VARCHAR(100),
    state VARCHAR(50),
    postcode VARCHAR(20),
    open_time TIME,
    close_time TIME,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- 2. Create Deal Table
CREATE TABLE deal (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    restaurant_id UUID NOT NULL,
    discount_percentage INTEGER NOT NULL CHECK (discount_percentage > 0 AND discount_percentage <= 100),
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_dine_in BOOLEAN DEFAULT TRUE,
    is_lightning BOOLEAN DEFAULT FALSE,
    quantity_available INTEGER DEFAULT 10 CHECK (quantity_available >= 0),
    active_date DATE DEFAULT CURRENT_DATE,
    metadata JSONB, -- Flexible column for extra attributes
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign Key Constraint
    CONSTRAINT fk_restaurant
        FOREIGN KEY (restaurant_id) 
        REFERENCES restaurant(id)
        ON DELETE CASCADE,
        
    -- Check Constraint: End time must be after start time (handling midnight crossing requires application logic or complex constraints)
    CONSTRAINT check_time_order CHECK (end_time > start_time)
);

-- 3. Create Indexes for Performance

-- Index for searching deals by time (Vital for Task 1)
CREATE INDEX idx_deal_time_window ON deal (start_time, end_time);

-- Index for finding all deals for a specific restaurant
CREATE INDEX idx_deal_restaurant_id ON