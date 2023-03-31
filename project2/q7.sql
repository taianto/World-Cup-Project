ALTER TABLE TEAM
ADD Constraint GROUP_CHECK CHECK (GROUP = 'Group A' or
           GROUP = 'Group B' or
           GROUP = 'Group C' or
           GROUP = 'Group D' or
           GROUP = 'Group E' or
           GROUP = 'Group F' or
           GROUP = 'Group G' or
           GROUP = 'Group H'
    );

INSERT INTO TEAM (country, name, website_url, group) VALUES
('Japan', 'Japan Football Association','http://www.jfa.jp/eng/','Group I');
