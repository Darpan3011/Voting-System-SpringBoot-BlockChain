-- Insert default admin user (password: admin123)
INSERT INTO users (username, email, password_hash, first_name, last_name, role, is_verified, is_active) 
VALUES ('admin', 'admin@voting.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'System', 'Administrator', 'ADMIN', true, true);

-- Insert election official
INSERT INTO users (username, email, password_hash, first_name, last_name, role, is_verified, is_active) 
VALUES ('election_official', 'official@voting.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Election', 'Official', 'ELECTION_OFFICIAL', true, true);

-- Insert sample voters
INSERT INTO users (username, email, password_hash, first_name, last_name, role, is_verified, is_active) 
VALUES 
('voter1', 'voter1@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'John', 'Doe', 'VOTER', true, true),
('voter2', 'voter2@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Jane', 'Smith', 'VOTER', true, true),
('voter3', 'voter3@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Bob', 'Johnson', 'VOTER', true, true);

-- Insert sample election
INSERT INTO elections (title, description, start_date, end_date, status, max_votes_per_voter, created_by) 
VALUES (
    'Student Council Election 2024',
    'Annual election for student council members. Vote for your preferred candidates.',
    DATE_ADD(NOW(), INTERVAL 1 DAY),
    DATE_ADD(NOW(), INTERVAL 7 DAY),
    'DRAFT',
    1,
    1
);

-- Insert candidates for the election
INSERT INTO candidates (election_id, name, party, description, image_url) 
VALUES 
(1, 'Alice Johnson', 'Progressive Party', 'Experienced student leader with focus on academic excellence', 'https://example.com/alice.jpg'),
(1, 'Mike Chen', 'Innovation Party', 'Tech-savvy candidate promoting digital transformation', 'https://example.com/mike.jpg'),
(1, 'Sarah Williams', 'Unity Party', 'Advocate for inclusive campus environment', 'https://example.com/sarah.jpg'); 