import { useEffect, useState } from "react";
import {
  Image,
  Spinner,
  VStack,
  Text,
  Table,
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
    <Table.Root size="sm" showColumnBorder>
      <Table.Header>
        <Table.Row>
          <Table.ColumnHeader>Data</Table.ColumnHeader>
          <Table.ColumnHeader textAlign="end">Kwota</Table.ColumnHeader>
          <Table.ColumnHeader>Category</Table.ColumnHeader>
          <Table.ColumnHeader>Miejsce</Table.ColumnHeader>
          <Table.ColumnHeader>Opis</Table.ColumnHeader>
          <Table.ColumnHeader>Zdjęcie</Table.ColumnHeader>
        </Table.Row>
      </Table.Header>
      <Table.Body>
        {items.map((item) => (
          <Table.Row key={item.id}>
              <Table.Cell>{item.date}</Table.Cell>
              <Table.Cell textAlign="end">{item.amount}</Table.Cell>
              <Table.Cell>{item.category}</Table.Cell>
              <Table.Cell>{item.place}</Table.Cell>
              <Table.Cell>{item.description}</Table.Cell>
              <Table.Cell>
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
              </Table.Cell>
          </Table.Row>
        ))}
      </Table.Body>
    </Table.Root>
  );
};

export default ShoppingListView;
