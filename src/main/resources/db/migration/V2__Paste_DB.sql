INSERT INTO public.manager
VALUES (-1, false, '$2a$10$l225sl3S3.AoehNpWkVE.uuFH3uMFW/K/3LW7JV7pnjM.zD1SWcnS', 'MainManager', 'Oleg');

INSERT INTO public.item
VALUES (-1, 'Apple M1', true,
        '13.3" 2560 x 1600 IPS, 60 Гц, несенсорный, Apple M1 3200 МГц, 8 ГБ, SSD 256 ГБ, видеокарта встроенная, Mac OS, цвет крышки серый',
        'SSD 256', 'Ноутбук Apple Macbook Air 13" M1 2020 MGN63', '2020-03-15', 'laptop');
INSERT INTO public.item
VALUES (-2, 'Apple M1 Pro', true,
        '14.2" 3024 x 1964 IPS, 120 Гц, несенсорный, Apple M1 Pro (8 ядер), 16 ГБ, SSD 512 ГБ, видеокарта встроенная, Mac OS, цвет крышки серебристый',
        'SSD 512', 'Ноутбук Apple Macbook Pro 14" M1 Pro 2021 MKGR3', '2021-01-11', 'laptop');
INSERT INTO public.item
VALUES (-3, 'Apple M1 Max', true,
        '16.2" 3456 x 2234 IPS, 120 Гц, несенсорный, Apple M1 Max, 32 ГБ, SSD 512 ГБ, видеокарта встроенная, Mac OS, цвет крышки серый',
        'SSD 512', 'Ноутбук Apple Macbook Pro 16" M1 Max 2021 Z14V0008F', '2021-07-17', 'laptop');
INSERT INTO public.item
VALUES (-4, 'Apple A15 Bionic', true,
        'Apple iOS, экран 6.7" OLED (1284x2778), Apple A15 Bionic, ОЗУ 6 ГБ, флэш-память 1 ТБ, камера 12 Мп, 1 SIM',
        'флэш-память 1 ТБ', 'Смартфон Apple iPhone 13 Pro Max 1TB (золотой)', '2021-06-13', 'iPhone');
INSERT INTO public.item
VALUES (-5, 'Apple A15 Bionic', true,
        'Apple iOS, экран 6.7" OLED (1284x2778), Apple A15 Bionic, ОЗУ 6 ГБ, флэш-память 512 ГБ, камера 12 Мп, 1 SIM',
        'флэш-память 512 ГБ', 'Смартфон Apple iPhone 13 Pro Max 512GB (небесно-голубой)', '2021-02-15', 'iPhone');
INSERT INTO public.item
VALUES (-6, 'IOS', true,
        'часы-компаньон, поддержка iOS, экран AMOLED, шагомер, пульсометр, время работы: 18 часов, корпус: сталь, браслет: металл',
        'флэш-память 32 ГБ', 'Умные часы Apple Watch Series 7 LTE 45 мм (сталь графитовый/миланский черный)',
        '2021-04-18', 'Smart watch');
INSERT INTO public.item
VALUES (-7, 'IOS', true,
        'беспроводные наушники с микрофоном, внутриканальные, портативные, Bluetooth, быстрая зарядка, время работы 5 ч, с кейсом 24 ч, активное шумоподавление',
        'IOS', 'Наушники Apple AirPods Pro (с поддержкой MagSafe)', '2021-07-18', 'Headphones');


INSERT INTO public.store_item
VALUES (1, true, 2000.00, 1, 1);
INSERT INTO public.store_item
VALUES (2, true, 3500.00, 2, 1);
INSERT INTO public.store_item
VALUES (3, true, 6200.00, 3, 1);
INSERT INTO public.store_item
VALUES (4, true, 2700.00, 4, 1);
INSERT INTO public.store_item
VALUES (5, true, 2400.00, 5, 1);
INSERT INTO public.store_item
VALUES (6, true, 1600.00, 6, 1);
INSERT INTO public.store_item
VALUES (7, true, 300.00, 7, 1);