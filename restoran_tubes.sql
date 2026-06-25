-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 25, 2026 at 04:01 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `restoran_tubes`
--

-- --------------------------------------------------------

--
-- Table structure for table `bahan_masakan`
--

CREATE TABLE `bahan_masakan` (
  `id_bahan` int NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `harga_barang` varchar(100) NOT NULL,
  `stok_barang` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gudang`
--

CREATE TABLE `gudang` (
  `id` int NOT NULL,
  `nama_barang` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `harga_barang` int DEFAULT NULL,
  `stock_barang` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gudang`
--

INSERT INTO `gudang` (`id`, `nama_barang`, `harga_barang`, `stock_barang`) VALUES
(11, 'Telur', 3000, 55);

-- --------------------------------------------------------

--
-- Table structure for table `masakan`
--

CREATE TABLE `masakan` (
  `id_masakan` int NOT NULL,
  `nama_masakan` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `harga` double NOT NULL,
  `status` varchar(50) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `masakan`
--

INSERT INTO `masakan` (`id_masakan`, `nama_masakan`, `harga`, `status`) VALUES
(1, 'Nasi Goreng Spesial', 25000, 'Tersedia'),
(2, 'Mie Goreng Seafood', 28000, 'Tersedia'),
(3, 'Ayam Bakar Madura', 32000, 'Tersedia'),
(4, 'Es Teh Manis', 5000, 'Tersedia');

-- --------------------------------------------------------

--
-- Table structure for table `menu_dapur`
--

CREATE TABLE `menu_dapur` (
  `id` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `nama` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `kategori` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `harga` double NOT NULL,
  `stok` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu_dapur`
--

INSERT INTO `menu_dapur` (`id`, `nama`, `kategori`, `harga`, `stok`) VALUES
('MNU-10001', 'Nasi Goreng Spesial', 'Makanan', 25000, 30),
('MNU-10002', 'Mie Goreng Seafood', 'Makanan', 28000, 20),
('MNU-10003', 'Ayam Bakar Madu', 'Makanan', 32000, 15),
('MNU-10004', 'Es Teh Manis', 'Minuman', 5000, 50),
('MNU-10005', 'Jus Alpukat Kocok', 'Minuman', 15000, 25),
('MNU-10006', 'Coffee Latte Art', 'Minuman', 20000, 20),
('MNU-10007', 'Kentang Goreng Keju', 'Cemilan', 12000, 40),
('MNU-10008', 'Roti Bakar Cokelat Susu', 'Cemilan', 15000, 15);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `id_menu` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `nama_menu` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `jumlah` int NOT NULL,
  `harga_satuan` double NOT NULL,
  `tanggal` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(50) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id`, `id_menu`, `nama_menu`, `jumlah`, `harga_satuan`, `tanggal`, `status`) VALUES
('TRX-98001', 'MNU-10001', 'Nasi Goreng Spesial', 2, 25000, '2026-06-21 12:30:15', 'Selesai'),
('TRX-98002', 'MNU-10004', 'Es Teh Manis', 2, 5000, '2026-06-21 12:30:15', 'Selesai'),
('TRX-98003', 'MNU-10003', 'Ayam Bakar Madu', 1, 32000, '2026-06-21 13:15:22', 'Selesai'),
('TRX-98004', 'MNU-10005', 'Jus Alpukat Kocok', 1, 15000, '2026-06-21 13:15:22', 'Selesai'),
('TRX-98005', 'MNU-10007', 'Kentang Goreng Keju', 3, 12000, '2026-06-21 14:00:00', 'Dibatalkan');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `role` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'kasir1', '12345', 'Kasir'),
(2, 'dapur1', '12345', 'Manajemen Dapur'),
(3, 'logistik1', '12345', 'Manajemen Logistik/gudang'),
(4, 'sel', '12345', 'Kasir'),
(5, 'dila', '12345', 'Kasir'),
(6, 'didil', 'didil04', 'Kasir');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bahan_masakan`
--
ALTER TABLE `bahan_masakan`
  ADD PRIMARY KEY (`id_bahan`);

--
-- Indexes for table `gudang`
--
ALTER TABLE `gudang`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `masakan`
--
ALTER TABLE `masakan`
  ADD PRIMARY KEY (`id_masakan`);

--
-- Indexes for table `menu_dapur`
--
ALTER TABLE `menu_dapur`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bahan_masakan`
--
ALTER TABLE `bahan_masakan`
  MODIFY `id_bahan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `gudang`
--
ALTER TABLE `gudang`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
