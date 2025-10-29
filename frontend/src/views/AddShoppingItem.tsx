import React, { useState } from "react";
import {
  Box,
  Button,
  Input,
  VStack,
  Image,
} from "@chakra-ui/react";
import axios from "axios";
import { toaster } from "../components/ui/toaster";
import { FormControl, FormLabel } from "@chakra-ui/form-control";

const AddShoppingItem: React.FC = () => {
  const [name, setName] = useState("");
  const [amount, setAmount] = useState<number | "">("");
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [preview, setPreview] = useState<string | null>(null);

//   const toast = useToast();

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0] || null;
    setImageFile(file);

    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => setPreview(reader.result as string);
      reader.readAsDataURL(file);
    } else {
      setPreview(null);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!name || !amount || !imageFile) {
        toaster.create({
          description: "Wypełnij wszystkie pola",
          type: "error",
        })
      return;
    }

    const formData = new FormData();
    formData.append("name", name);
    formData.append("amount", amount.toString());
    formData.append("file", imageFile);

    try {
      await axios.post("http://localhost:8080/api/shopping/notUsed", formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
toaster.create({
          description: "Dodano przedmiot",
          type: "success",
        })

      // reset
      setName("");
      setAmount("");
      setImageFile(null);
      setPreview(null);
    } catch (error) {
        toaster.create({
          description: "Błąd podczas dodawania",
          type: "error",
        })
    }
  };

  return (
    <Box maxW="md" mx="auto" mt={8} p={6} borderWidth={1} borderRadius="md">
      <form onSubmit={handleSubmit}>
        <VStack align="stretch">
          <FormControl isRequired>
            <FormLabel>Nazwa produktu</FormLabel>
            <Input
              placeholder="Nazwa"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </FormControl>

          <FormControl isRequired>
            <FormLabel>Kwota</FormLabel>
            <Input
              type="number"
              placeholder="Cena"
              value={amount}
              onChange={(e) =>
                setAmount(e.target.value === "" ? "" : Number(e.target.value))
              }
            />
          </FormControl>

          <FormControl isRequired>
            <FormLabel>Zdjęcie</FormLabel>
            <Input type="file" accept="image/*" onChange={handleImageChange} />
          </FormControl>

          {preview && (
            <Image
              src={preview}
              alt="Podgląd zdjęcia"
              maxH="200px"
              objectFit="cover"
              borderRadius="md"
            />
          )}

          <Button colorScheme="teal" type="submit">
            Dodaj przedmiot
          </Button>
        </VStack>
      </form>
    </Box>
  );
};

export default AddShoppingItem;
