USE Blog
INSERT INTO tbl_lookup (name, code, type, position) 
VALUES 
(N'Draft', 1, N'PostStatus', 1),
(N'Published', 2, N'PostStatus', 2),
(N'Archived', 3, N'PostStatus', 3);

INSERT INTO [dbo].[tbl_user]
VALUES ('123', 'Admin')