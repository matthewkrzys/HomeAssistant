import { useEffect, useState } from "react";
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Image,
  Spinner,
  VStack,
  Text,
} from "@chakra-ui/react";
import axios from "axios";


interface ShoppingItem {
  id: number;
  date: string;
  amount: number;
  category: string;
  place: string;
  description: string;
  image?: string;
}

const ShoppingListView = () => {
  const [items, setItems] = useState<ShoppingItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchItems = async () => {
      try {
        const res = await axios.get<ShoppingItem[]>("http://localhost:8080/api/shopping");
        setItems(res.data);
      } catch (err) {
        setError("Nie udało się pobrać danych 😔");
      } finally {
        setLoading(false);
      }
    };
    fetchItems();
  }, []);

  if (loading)
    return (
      <VStack align="center" justify="center" height="100%">
        <Spinner size="xl" />
        <Text>Ładowanie danych...</Text>
      </VStack>
    );

  if (error)
    return (
      <VStack align="center" justify="center" height="100%">
        <Text color="red.500">{error}</Text>
      </VStack>
    );

  return (
    <TableContainer>
      <Table variant="simple" colorScheme="gray">
        <Thead>
          <Tr>
            <Th>ID</Th>
            <Th>Data</Th>
            <Th>Kwota (zł)</Th>
            <Th>Kategoria</Th>
            <Th>Miejsce</Th>
            <Th>Opis</Th>
            <Th>Zdjęcie</Th>
          </Tr>
        </Thead>
        <Tbody>
          {items.map((item) => (
            <Tr key={item.id}>
              <Td>{item.id}</Td>
              <Td>{item.date}</Td>
              <Td>{item.amount.toFixed(2)}</Td>
              <Td>{item.category}</Td>
              <Td>{item.place}</Td>
              <Td>{item.description}</Td>
              <Td>
                {item.image ? (
                  // jeśli Base64
                  <Image
                    src={`data:image/jpeg;base64,${item.image}`}
                    alt={item.description}
                    boxSize="60px"
                    objectFit="cover"
                    borderRadius="md"
                  />
                ) : (
                  <Text>Brak zdjęcia</Text>
                )}
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </TableContainer>
  );
};

export default ShoppingListView;
