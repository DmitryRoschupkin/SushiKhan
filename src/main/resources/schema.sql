CREATE TABLE IF NOT EXISTS sushi_order(
	sushi_order_id BIGSERIAL PRIMARY KEY,
	delivery_name VARCHAR(50) NOT NULL,
	delivery_street VARCHAR(50) NOT NULL,
	delivery_house VARCHAR(50) NOT NULL,
	delivery_city VARCHAR(50) NOT NULL,
	delivery_region VARCHAR(50) NOT NULL,
	delivery_flat VARCHAR(50) NOT NULL,
	delivery_entrance VARCHAR(50) NOT NULL,
	cc_number VARCHAR(16) NOT NULL,
	cc_expiration VARCHAR(5) NOT NULL,
	cc_cvv VARCHAR(3) NOT NULL,
	placed_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS sushi(
	sushi_id SERIAL PRIMARY KEY,
	name varchar(50) NOT NULL,
	sushi_order_id BIGINT NOT NULL,
	sushi_order_key BIGINT NOT NULL,
	created_at TIMESTAMP NOT NULL,
	FOREIGN KEY(sushi_order_id) REFERENCES sushi_order(sushi_order_id)
);

CREATE TABLE IF NOT EXISTS ingredient(
	ingredient_id VARCHAR(4) PRIMARY KEY,
	name VARCHAR(50),
	type VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS ingredient_ref(
	ingredient_id VARCHAR(4) NOT NULL,
	sushi_id BIGINT NOT NULL,
	sushi_key BIGINT NOT NULL,
	FOREIGN KEY (ingredient_id) REFERENCES ingredient(ingredient_id),
	FOREIGN KEY (sushi_id) REFERENCES sushi(sushi_id)
)